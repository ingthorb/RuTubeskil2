package data;

import Exceptions.InvalidPasswordException;
import Exceptions.UnauthorizedException;
import Exceptions.UserAlreadyExistsException;
import is.ruframework.data.RuData;
import models.ChangePasswordModel;
import models.TokenModel;
import models.UserModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laufey on 31/10/2016.
 */


public class AccountData extends RuData implements AccountDataGateway{

    public int addUser(UserModel user) throws UserAlreadyExistsException{
        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(getDataSource())
                        .withTableName("users")
                        .usingColumns("fullName","userName","email","password")
                        .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>(4);
        parameters.put("fullName", user.getFullName());
        parameters.put("userName", user.getUserName());
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        int returnKey = 0;

        try
        {
            returnKey = insertContent.executeAndReturnKey(parameters).intValue();
        }
        catch (DataIntegrityViolationException divex)
        {
            log.warning("Duplicate entry");
            throw new UserAlreadyExistsException();
        }
        return returnKey;
    }

    public int addToken(TokenModel token){
        int returnKey = 0;

        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(getDataSource())
                        .withTableName("token")
                        .usingColumns("userName","token");


        Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put("userName", token.getUserName());
        parameters.put("token", token.getToken());

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());
        List<UserModel> userExists = queryContent.query("select * from users where userName ='" + token.getUserName() + "'", new UserRowMapper());

        if(userExists.size() != 0)
        {
            List<TokenModel> tokenExists = queryContent.query("select * from token where userName ='" + token.getUserName() + "'", new TokenRowMapper());

            if(tokenExists.size() != 0){
                System.out.println("update token set token ='" + token.getToken() + "' WHERE userName = '" + token.getUserName() +"'");

                queryContent.execute("update token set token ='" + token.getToken() + "' WHERE userName = '" + token.getUserName() +"'");
                //TODO test update
            }
            else{
                returnKey = insertContent.execute(parameters);
            }
        }
        return returnKey;
    }

    public boolean checkIfPasswordMatches(String user,String userExists){
        if(userExists.toString().equals(user))
        {
            return true;
        }
        return false;
    }

    public UserModel checkIfUserExists(UserModel user){
        int returnKey = 0;
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<UserModel> userExists = queryContent.query("select * from users where userName ='" + user.getUserName() + "'", new UserRowMapper());

        return userExists.get(0);
    }


    public String getUserNameFromToken(String token) throws UnauthorizedException {

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<TokenModel> tokenData = queryContent.query("select * from token where token ='" + token + "'", new TokenRowMapper());

        if(tokenData.size() == 0){
            throw new UnauthorizedException();
        }
        return tokenData.get(0).getUserName();
    }

    public void ChangePassword(ChangePasswordModel passwordModel, String username) throws InvalidPasswordException {
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<UserModel> userExists = queryContent.query("select * from users where userName ='" + username + "'", new UserRowMapper());

        if(checkIfPasswordMatches(passwordModel.getOldPassword(),userExists.get(0).getPassword())){
            queryContent.execute("update users set password ='" + passwordModel.getNewPassword() + "' WHERE userName = '" + username +"'");
        }
        else{
            throw new InvalidPasswordException();
        }
    }

    public void DeleteUser(int id){
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<UserModel> userExists = queryContent.query("select * from users where id ='" + id + "'", new UserRowMapper());

        queryContent.execute("DELETE FROM token WHERE userName='" + userExists.get(0).getUserName() + "'");
        queryContent.execute("DELETE FROM users WHERE userName='" + userExists.get(0).getUserName() + "'");
    }

}
