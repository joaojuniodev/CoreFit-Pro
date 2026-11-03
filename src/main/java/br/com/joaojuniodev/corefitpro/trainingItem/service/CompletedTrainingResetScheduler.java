package br.com.joaojuniodev.corefitpro.trainingItem.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@org.springframework.scheduling.annotation.EnableScheduling
public class CompletedTrainingResetScheduler {

    private final TrainingItemService trainingItemService;

    public CompletedTrainingResetScheduler(TrainingItemService trainingItemService) {
        this.trainingItemService = trainingItemService;
    }

    @Scheduled(cron = "0 0 0 * * MON", zone = "America/Sao_Paulo")
    @org.springframework.context.event.EventListener(org.springframework.boot.context.event.ApplicationReadyEvent.class)
    public void resetsWorkoutsMarkedAsCompletedFotTheWeek() {
        trainingItemService.resetCompletedWorkouts();
    }
}
