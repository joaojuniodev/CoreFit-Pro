package db.migration;

import br.com.joaojuniodev.corefitpro.security.password.PasswordEncoding;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import java.sql.SQLException;

public class V34__HASH_LEGACY_TRAINEE_PASSWORDS extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws SQLException {
        var encoder = PasswordEncoding.createEncoder();
        String select = """
            SELECT u.id, u.password
            FROM users u
            WHERE u.password IS NOT NULL AND u.password <> ''
              AND EXISTS (SELECT 1 FROM trainees t WHERE t.user_id = u.id)
            """;
        try (var statement = context.getConnection().prepareStatement(select);
             var rows = statement.executeQuery();
             var update = context.getConnection().prepareStatement("UPDATE users SET password = ? WHERE id = ?")) {
            while (rows.next()) {
                String password = rows.getString("password");
                if (PasswordEncoding.isEncoded(password) || password.matches("^\\{[^}]+}.*")) continue;
                update.setString(1, encoder.encode(password));
                update.setObject(2, rows.getObject("id"));
                update.executeUpdate();
            }
        }
    }
}
