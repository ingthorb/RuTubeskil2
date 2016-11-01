package TestModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by Ingthor on 1.11.2016.
 */
public class Login {

        public final static  String mediaType = "application/json";


        @NotNull
        @JsonProperty("userName")
        public String userName;


        @NotNull
        @JsonProperty("password")
        public String password;

        public Login() {
        }

        public Login( String userName, String password) {
            this.userName = userName;
            this.password = password;
        }


        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }




