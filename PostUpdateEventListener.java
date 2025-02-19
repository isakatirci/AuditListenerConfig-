import lombok.RequiredArgsConstructor;  
import lombok.extern.slf4j.Slf4j;  
import org.hibernate.event.internal.PostUpdateEventListenerStandardImpl;  
import org.hibernate.event.spi.PostUpdateEvent;  
import org.springframework.stereotype.Component;  

/**  
 * Custom PostUpdateEventListener to handle logic after an entity is updated.  
 */  
@Component  
@RequiredArgsConstructor  
@Slf4j  
public class PostUpdateEventListener extends PostUpdateEventListenerStandardImpl {  

    // Example: Inject a service to handle post-update logic  
    private final SomeService someService;  

    @Override  
    public void onPostUpdate(PostUpdateEvent event) {  
        log.info("PostUpdateEvent triggered for entity: {}", event.getEntityName());  

        try {  
            // Process the updated entity  
            Object entity = event.getEntity();  
            if (entity != null) {  
                handleEntityUpdate(entity);  
            } else {  
                log.warn("Entity is null in PostUpdateEvent for: {}", event.getEntityName());  
            }  
        } catch (Exception e) {  
            log.error("Error processing PostUpdateEvent for entity: {}", event.getEntityName(), e);  
        } finally {  
            // Ensure the parent implementation is called  
            super.onPostUpdate(event);  
        }  
    }  

    /**  
     * Handles the logic for processing the updated entity.  
     *  
     * @param entity the updated entity  
     */  
    private void handleEntityUpdate(Object entity) {  
        // Example: Perform some logic with the updated entity  
        log.info("Handling update for entity: {}", entity);  

        // Example: Call a service to handle the update  
        someService.processEntityUpdate(entity);  
    }  
}
