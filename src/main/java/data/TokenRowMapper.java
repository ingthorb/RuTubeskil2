package data;

import models.TokenModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Laufey on 31/10/2016.
 */
public class TokenRowMapper implements RowMapper<TokenModel>
{
    public TokenModel mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        TokenModel token = new TokenModel (
                rs.getString (1),       // username
                rs.getString (2)     // token
        );
        return token;
    }
}
