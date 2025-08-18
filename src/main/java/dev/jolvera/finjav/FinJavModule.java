package dev.jolvera.finjav;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dev.jolvera.finjav.services.interfaces.UserService;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.sqlite.SQLiteDataSource;
import jakarta.inject.Singleton;
import javax.sql.DataSource;

@Module
public abstract class FinJavModule {
    @Provides
    @Singleton
    static DataSource provideDataSource() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:finjav.sqlite");
        return ds;
    }

    @Provides
    @Singleton
    static Jdbi ProvideJdbi(DataSource dataSource) {
        return Jdbi.create(dataSource)
                .installPlugin(new SqlObjectPlugin());
    }

    @Binds
    @Singleton
    abstract UserService bindUserService(UserServiceImpl userServiceImpl);
}
