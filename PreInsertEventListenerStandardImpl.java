package com.example.hibernate.listener;  

import lombok.RequiredArgsConstructor;  
import lombok.extern.slf4j.Slf4j;  
import org.hibernate.event.internal.PreInsertEventListenerStandardImpl;  
import org.hibernate.event.spi.PreInsertEvent;  
import org.springframework.stereotype.Component;  

import com.example.service.SomeService;  

/**  
 * Custom PreInsert event listener that extends Hibernate's PreInsertEventListenerStandardImpl.  
 * This listener performs custom pre-insert processing before an entity is persisted.  
 */  
@Component  
@RequiredArgsConstructor  
@Slf4j  
public class CustomPreInsertEventListener extends PreInsertEventListenerStandardImpl {  

    private final SomeService someService;  

    /**  
     * This method is called by Hibernate before an entity is inserted.  
     *   
     * @param event the PreInsertEvent that contains details about the entity insert.  
     * @return false to allow the insert operation to proceed.  
     */  
    @Override  
    public boolean onPreInsert(PreInsertEvent event) {  
        log.info("Pre-insert event triggered for entity: {}", event.getEntityName());  
        try {  
            Object entity = event.getEntity();  
            if (entity != null) {  
                processEntityPreInsert(entity);  
            } else {  
                log.warn("Received null entity in pre-insert event for: {}", event.getEntityName());  
            }  
        } catch (Exception e) {  
            log.error("Error during pre-insert event processing for entity: {}", event.getEntityName(), e);  
        }  
        // Returning super call ensures any default processing is preserved.  
        return super.onPreInsert(event);  
    }  

    /**  
     * Processes the entity before it is inserted into the database.  
     *  
     * @param entity the entity that is about to be inserted.  
     */  
    private void processEntityPreInsert(Object entity) {  
        log.info("Processing pre-insert for entity: {}", entity);  
        // Execute any pre-insert business logic through the injected service.  
        someService.onPreInsert(entity);  
    }  
}
