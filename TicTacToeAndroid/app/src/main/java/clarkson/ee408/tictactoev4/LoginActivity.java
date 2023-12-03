package clarkson.ee408.tictactoev4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import clarkson.ee408.tictactoev4.client.SocketClient;
import clarkson.ee408.tictactoev4.model.User;
import clarkson.ee408.tictactoev4.socket.Request;
import clarkson.ee408.tictactoev4.socket.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Getting UI elements
        Button loginButton = findViewById(R.id.buttonLogin);
        Button registerButton = findViewById(R.id.buttonRegister);
        usernameField = findViewById(R.id.editTextUsername);
        passwordField = findViewById(R.id.editTextPassword);

        // Initialize Gson with null serialization option
        gson = new GsonBuilder().serializeNulls().create();
        //Adding Handlers
        loginButton.setOnClickListener(view -> handleLogin());
        registerButton.setOnClickListener(view -> gotoRegister());
    }

    /**
     * Process login input and pass it to {@link #submitLogin(User)}
     */
    public void handleLogin() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        // verify that all fields are not empty before proceeding. Toast with the error message
        if(username.isEmpty())
        {
            Toast.makeText(this, "No username was provided", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty())
        {
            Toast.makeText(this, "No password was provided", Toast.LENGTH_SHORT).show();
            return;
        }
        // Create User object with username and password and call submitLogin()
        submitLogin(new User(username, password, null, false));
    }

    /**
     * Sends a LOGIN request to the server
     * @param user User object to login
     */
    public void submitLogin(User user) {
        // Send a LOGIN request, If SUCCESS response, call gotoPairing(), else, Toast the error message from sever
        Response response = SocketClient.getInstance().sendRequest(new Request(Request.RequestType.LOGIN, gson.toJson(user)), Response.class);

        if(response == null)
        {
            Toast.makeText(this, "no response from server", Toast.LENGTH_SHORT).show();
            return;
        }
        if(response.getStatus() != Response.ResponseStatus.SUCCESS) {
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        gotoPairing(user.getUsername());
    }

    /**
     * Switch the page to {@link PairingActivity}
     * @param username the data to send
     */
    public void gotoPairing(String username) {
        // start PairingActivity and pass the username
        Intent intent = new Intent(this, PairingActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * Switch the page to {@link RegisterActivity}
     */
    public void gotoRegister() {
        // start RegisterActivity
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}