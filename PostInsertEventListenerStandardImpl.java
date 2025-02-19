package com.example.hibernate.listener;  

import lombok.RequiredArgsConstructor;  
import lombok.extern.slf4j.Slf4j;  
import org.hibernate.event.internal.PostInsertEventListenerStandardImpl;  
import org.hibernate.event.spi.PostInsertEvent;  
import org.springframework.stereotype.Component;  
import com.example.service.SomeService;  

/**  
 * Custom PostInsert event listener that extends Hibernate's internal   
 * PostInsertEventListenerStandardImpl.  
 *  
 * This listener handles logic that should be executed after an entity is inserted  
 * into the database.  
 */  
@Component  
@RequiredArgsConstructor  
@Slf4j  
public class CustomPostInsertEventListener extends PostInsertEventListenerStandardImpl {  

    private final SomeService someService;  

    /**  
     * Called after an entity insertion event. This method allows custom processing  
     * after the entity has been inserted into the DB.  
     *  
     * @param event the PostInsertEvent providing details about the inserted entity  
     */  
    @Override  
    public void onPostInsert(PostInsertEvent event) {  
        log.info("Post-insert event triggered for entity: {}", event.getEntityName());  
        try {  
            Object entity = event.getEntity();  
            if (entity != null) {  
                processEntityPostInsert(entity);  
            } else {  
                log.warn("Received null entity in post-insert event for: {}", event.getEntityName());  
            }  
        } catch (Exception e) {  
            log.error("Error during post-insert event processing for entity: {}", event.getEntityName(), e);  
        } finally {  
            // Call the superclass method to ensure default behavior is retained  
            super.onPostInsert(event);  
        }  
    }  

    /**  
     * Processes the entity after it has been inserted into the database.  
     *  
     * @param entity the inserted entity to process  
     */  
    private void processEntityPostInsert(Object entity) {  
        log.info("Processing post-insert for entity: {}", entity);  
        // Call a custom service method to handle the inserted entity.  
        someService.onPostInsert(entity);  
    }  
}
