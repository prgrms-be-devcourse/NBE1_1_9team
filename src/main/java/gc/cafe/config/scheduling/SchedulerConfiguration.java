package gc.cafe.config.scheduling;

import gc.cafe.model.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerConfiguration {

    private final OrderService orderService;

    @Scheduled(cron = "* * 14 * * *")
    public void run() {
        orderService.approveOrders();
    }
}
