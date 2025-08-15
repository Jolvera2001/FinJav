package dev.jolvera.finjav.modules;

import dagger.Module;
import dagger.Provides;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Module
public class FinJavModule {
    @Provides
    @Singleton
    public Jdbi ProvideJdbi(DataSource dataSource) {
        return Jdbi.create(dataSource)
                .installPlugin(new SqlObjectPlugin());
    }
}
