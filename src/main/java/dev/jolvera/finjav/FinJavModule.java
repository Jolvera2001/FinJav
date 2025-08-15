package dev.jolvera.finjav;

import dagger.Module;
import dagger.Provides;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.sqlite.SQLiteDataSource;
import jakarta.inject.Singleton;
import javax.sql.DataSource;

@Module
public class FinJavModule {
    @Provides
    @Singleton
    public DataSource provideDataSource() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:finjav.db");
        return ds;
    }

    @Provides
    @Singleton
    public Jdbi ProvideJdbi(DataSource dataSource) {
        return Jdbi.create(dataSource)
                .installPlugin(new SqlObjectPlugin());
    }
}
