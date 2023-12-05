package clarkson.ee408.tictactoev4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import clarkson.ee408.tictactoev4.client.AppExecutors;
import clarkson.ee408.tictactoev4.client.SocketClient;
import clarkson.ee408.tictactoev4.model.Event;
import clarkson.ee408.tictactoev4.model.User;
import clarkson.ee408.tictactoev4.socket.PairingResponse;
import clarkson.ee408.tictactoev4.socket.Request;
import clarkson.ee408.tictactoev4.socket.Response;

public class PairingActivity extends AppCompatActivity {

    private final String TAG = "PAIRING";

    private Gson gson;

    private TextView noAvailableUsersText;
    private RecyclerView recyclerView;
    private AvailableUsersAdapter adapter;

    private Handler handler;
    private Runnable refresh;

    private boolean shouldUpdatePairing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);

        Log.e(TAG, "App is now created");
        // setup Gson with null serialization option
        gson = new GsonBuilder().serializeNulls().create();

        //Setting the username text
        TextView usernameText = findViewById(R.id.text_username);
        // set the usernameText to the username passed from LoginActivity (i.e from Intent)
        usernameText.setText(getIntent().getStringExtra("username"));

        //Getting UI Elements
        noAvailableUsersText = findViewById(R.id.text_no_available_users);
        recyclerView = findViewById(R.id.recycler_view_available_users);

        //Setting up recycler view adapter
        adapter = new AvailableUsersAdapter(this, this::sendGameInvitation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateAvailableUsers(null);

        handler = new Handler();
        refresh = () -> {
            // call getPairingUpdate if shouldUpdatePairing is true
            if(shouldUpdatePairing)
            {
                getPairingUpdate();
            }
            handler.postDelayed(refresh, 1000);
        };
        handler.post(refresh);
    }

    /**
     * Send UPDATE_PAIRING request to the server
     */
    private void getPairingUpdate() {
        // Send an UPDATE_PAIRING request to the server. If SUCCESS call handlePairingUpdate(). Else, Toast the error
        AppExecutors.getInstance().networkIO().execute(()-> {
            PairingResponse response = SocketClient.getInstance().sendRequest(new Request(Request.RequestType.UPDATE_PAIRING, ""), PairingResponse.class);
            AppExecutors.getInstance().mainThread().execute(()-> {
                if (response == null) {
                    Toast.makeText(this, "no response from server", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.getStatus() != Response.ResponseStatus.SUCCESS)
                {
                    Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                handlePairingUpdate(response);
            });
        });
    }

    /**
     * Handle the PairingResponse received form the server
     * @param response PairingResponse from the server
     */
    private void handlePairingUpdate(PairingResponse response) {
        // handle availableUsers by calling updateAvailableUsers()
        updateAvailableUsers(response.getAvailableUsers());

        if(response.getInvitationResponse() != null)
        {
            // handle invitationResponse. First by sending acknowledgement calling sendAcknowledgement()
            sendAcknowledgement(response.getInvitationResponse());

            // If the invitationResponse is ACCEPTED, Toast an accept message and call beginGame
            if(response.getInvitationResponse().getStatus() == Event.EventStatus.ACCEPTED)
            {
                Toast.makeText(this, "Game has been accepted", Toast.LENGTH_SHORT).show();
                beginGame(response.getInvitationResponse(), 1);
                return;
            }
            // If the invitationResponse is DECLINED, Toast a decline message
            else if(response.getInvitationResponse().getStatus() == Event.EventStatus.DECLINED)
            {
                Toast.makeText(this, "Game has been declined", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(response.getInvitation() != null)
        {
            // handle invitation by calling createRespondAlertDialog()
            createRespondAlertDialog(response.getInvitation());
        }
    }

    /**
     * Updates the list of available users
     * @param availableUsers list of users that are available for pairing
     */
    public void updateAvailableUsers(List<User> availableUsers) {
        adapter.setUsers(availableUsers);
        if (adapter.getItemCount() <= 0) {
            // show noAvailableUsersText and hide recyclerView
            recyclerView.setVisibility(View.GONE);
            noAvailableUsersText.setVisibility(View.VISIBLE);
        } else {
            // hide noAvailableUsersText and show recyclerView
            noAvailableUsersText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Sends game invitation to an
     * @param userOpponent the User to send invitation to
     */
    private void sendGameInvitation(User userOpponent) {
        // Send an SEND_INVITATION request to the server. If SUCCESS Toast a success message. Else, Toast the error
        AppExecutors.getInstance().networkIO().execute(()-> {
            Response response = SocketClient.getInstance().sendRequest(new Request(Request.RequestType.SEND_INVITATION, userOpponent.getUsername()), Response.class);
            AppExecutors.getInstance().mainThread().execute(()-> {
                if (response == null) {
                    Toast.makeText(this, "no response from server", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.getStatus() != Response.ResponseStatus.SUCCESS)
                {
                    Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "Sent Game Invitation", Toast.LENGTH_SHORT).show();
            });
        });
    }

    /**
     * Sends an ACKNOWLEDGE_RESPONSE request to the server
     * Tell server i have received accept or declined response from my opponent
      */
    private void sendAcknowledgement(Event invitationResponse) {
        // Send an ACKNOWLEDGE_RESPONSE request to the server.
        AppExecutors.getInstance().networkIO().execute(()-> {
            SocketClient.getInstance().sendRequest(new Request(Request.RequestType.ACKNOWLEDGE_RESPONSE, gson.toJson(invitationResponse.getEventId())), Response.class);
        });
    }

    /**
     * Create a dialog showing incoming invitation
     * @param invitation the Event of an invitation
     */
    private void createRespondAlertDialog(Event invitation) {
        // set shouldUpdatePairing to false
        shouldUpdatePairing = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Game Invitation");
        builder.setMessage(invitation.getSender() + " has Requested to Play with You");
        builder.setPositiveButton("Accept", (dialogInterface, i) -> acceptInvitation(invitation));
        builder.setNegativeButton("Decline", (dialogInterface, i) -> declineInvitation(invitation));
        builder.show();
    }

    /**
     * Sends an ACCEPT_INVITATION to the server
     * @param invitation the Event invitation to accept
     */
    private void acceptInvitation(Event invitation) {
        // Send an ACCEPT_INVITATION request to the server. If SUCCESS beginGame() as player 2. Else, Toast the error
        AppExecutors.getInstance().networkIO().execute(()-> {
            Response response = SocketClient.getInstance().sendRequest(new Request(Request.RequestType.ACCEPT_INVITATION, gson.toJson(invitation.getEventId())), Response.class);
            AppExecutors.getInstance().mainThread().execute(()-> {
                if (response == null) {
                    Toast.makeText(this, "no response from server", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.getStatus() != Response.ResponseStatus.SUCCESS)
                {
                    Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                beginGame(invitation, 2);
            });
        });
    }

    /**
     * Sends an DECLINE_INVITATION to the server
     * @param invitation the Event invitation to decline
     */
    private void declineInvitation(Event invitation) {
        // Send a DECLINE_INVITATION request to the server. If SUCCESS response, Toast a message, else, Toast the error
        AppExecutors.getInstance().networkIO().execute(()-> {
            Response response = SocketClient.getInstance().sendRequest(new Request(Request.RequestType.DECLINE_INVITATION, gson.toJson(invitation.getEventId())), Response.class);
            AppExecutors.getInstance().mainThread().execute(()-> {
                if (response == null) {
                    Toast.makeText(this, "no response from server", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.getStatus() != Response.ResponseStatus.SUCCESS)
                {
                    Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Declined Invitation", Toast.LENGTH_SHORT).show();
                }
                // set shouldUpdatePairing to true after DECLINE_INVITATION is sent.
                shouldUpdatePairing = true;
            });
        });
    }

    /**
     *
     * @param pairing the Event of pairing
     * @param player either 1 or 2
     */
    private void beginGame(Event pairing, int player) {
        // set shouldUpdatePairing to false
        shouldUpdatePairing = false;
        // start MainActivity and pass player as data
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("player", player);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // set shouldUpdatePairing to true
        shouldUpdatePairing = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);

        // set shouldUpdatePairing to false
        shouldUpdatePairing = false;
        // logout by calling close() function of SocketClient
        SocketClient.getInstance().close();
    }

}