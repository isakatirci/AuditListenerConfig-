import jakarta.annotation.PostConstruct;  
import lombok.RequiredArgsConstructor;  
import lombok.extern.slf4j.Slf4j;  
import org.hibernate.SessionFactory;  
import org.hibernate.event.service.spi.EventListenerRegistry;  
import org.hibernate.event.spi.EventType;  
import org.hibernate.internal.SessionFactoryImpl;  
import org.springframework.context.annotation.Configuration;  

@Configuration  
@RequiredArgsConstructor  
@Slf4j  
public class AuditListenerConfig {  

    private final EntityManagerFactory entityManagerFactory;  
    private final PostInsertEventListener postInsertEventListener;  
    private final PostUpdateEventListener postUpdateEventListener;  

    /**  
     * Initializes Hibernate event listeners for auditing purposes.  
     */  
    @PostConstruct  
    public void init() {  
        log.info("Initializing Hibernate event listeners...");  

        try {  
            // Unwrap the Hibernate SessionFactory from the EntityManagerFactory  
            SessionFactoryImpl sessionFactory = unwrapSessionFactory();  

            // Register the event listeners  
            registerEventListeners(sessionFactory);  

            log.info("Hibernate event listeners successfully registered.");  
        } catch (Exception e) {  
            log.error("Failed to initialize Hibernate event listeners.", e);  
            throw new IllegalStateException("Could not initialize Hibernate event listeners", e);  
        }  
    }  

    /**  
     * Unwraps the Hibernate SessionFactory from the EntityManagerFactory.  
     *  
     * @return the unwrapped SessionFactoryImpl  
     */  
    private SessionFactoryImpl unwrapSessionFactory() {  
        if (entityManagerFactory == null) {  
            throw new IllegalStateException("EntityManagerFactory is not initialized.");  
        }  
        return entityManagerFactory.unwrap(SessionFactoryImpl.class);  
    }  

    /**  
     * Registers the PostInsert and PostUpdate event listeners with Hibernate.  
     *  
     * @param sessionFactory the Hibernate SessionFactory  
     */  
    private void registerEventListeners(SessionFactoryImpl sessionFactory) {  
        EventListenerRegistry eventListenerRegistry = sessionFactory  
                .getServiceRegistry()  
                .getService(EventListenerRegistry.class);  

        if (eventListenerRegistry == null) {  
            throw new IllegalStateException("EventListenerRegistry is not available.");  
        }  

        // Register PostInsertEventListener  
        if (postInsertEventListener != null) {  
            eventListenerRegistry.appendListeners(EventType.POST_INSERT, postInsertEventListener);  
            log.info("PostInsertEventListener registered.");  
        } else {  
            log.warn("PostInsertEventListener is null. Skipping registration.");  
        }  

        // Register PostUpdateEventListener  
        if (postUpdateEventListener != null) {  
            eventListenerRegistry.appendListeners(EventType.POST_UPDATE, postUpdateEventListener);  
            log.info("PostUpdateEventListener registered.");  
        } else {  
            log.warn("PostUpdateEventListener is null. Skipping registration.");  
        }  
    }  
}
