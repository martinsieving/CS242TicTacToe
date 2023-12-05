package clarkson.ee408.tictactoev4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import clarkson.ee408.tictactoev4.client.AppExecutors;
import clarkson.ee408.tictactoev4.client.SocketClient;
import clarkson.ee408.tictactoev4.model.User;
import clarkson.ee408.tictactoev4.socket.Request;
import clarkson.ee408.tictactoev4.socket.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private EditText displayNameField;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Getting Inputs
        Button registerButton = findViewById(R.id.buttonRegister);
        Button loginButton = findViewById(R.id.buttonLogin);
        usernameField = findViewById(R.id.editTextUsername);
        passwordField = findViewById(R.id.editTextPassword);
        confirmPasswordField = findViewById(R.id.editTextConfirmPassword);
        displayNameField = findViewById(R.id.editTextDisplayName);

        // Initialize Gson with null serialization option
        gson = new GsonBuilder().serializeNulls().create();
        //Adding Handlers
        // set an onclick listener to registerButton to call handleRegister()
        registerButton.setOnClickListener(view -> handleRegister());
        // set an onclick listener to loginButton to call goBackLogin()
        loginButton.setOnClickListener(view -> goBackLogin());
    }

    /**
     * Process registration input and pass it to {@link #submitRegistration(User)}
     */
    public void handleRegister() {
        // declare local variables for username, password, confirmPassword and displayName. Initialize their values with their corresponding EditText
        String username = usernameField.getText().toString();
        String displayName = displayNameField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();
        // verify that all fields are not empty before proceeding. Toast with the error message
        if(username.isEmpty())
        {
            Toast.makeText(this, "No username was given", Toast.LENGTH_SHORT).show();
            return;
        }
        if(displayName.isEmpty())
        {
            Toast.makeText(this, "No display name was given", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty())
        {
            Toast.makeText(this, "No password was given", Toast.LENGTH_SHORT).show();
            return;
        }
        if(confirmPassword.isEmpty())
        {
            Toast.makeText(this, "Password was not confirmed", Toast.LENGTH_SHORT).show();
            return;
        }
        // verify that password is the same af confirm password. Toast with the error message
        if(!password.equals(confirmPassword))
        {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        // Create User object with username, display name and password and call submitRegistration()
        submitRegistration(new User(username, password, displayName, false));
    }

    /**
     * Sends REGISTER request to the server
     * @param user the User to register
     */
    void submitRegistration(User user) {
        // Send a REGISTER request to the server, if SUCCESS reponse, call goBackLogin(). Else, Toast the error message
        AppExecutors.getInstance().networkIO().execute(()-> {
            Response response = SocketClient.getInstance().sendRequest(new Request(Request.RequestType.REGISTER, gson.toJson(user)), Response.class);
            AppExecutors.getInstance().mainThread().execute(()-> {
                if(response == null)
                {
                    Toast.makeText(this, "no response from server", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.getStatus() != Response.ResponseStatus.SUCCESS)
                {
                    Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                goBackLogin();
            });
        });
    }

    /**
     * Change the activity to LoginActivity
     */
    private void goBackLogin() {
        // Close this activity by calling finish(), it will automatically go back to its parent (i.e,. LoginActivity)
        finish();
    }

}