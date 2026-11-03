package br.com.joaojuniodev.corefitpro.trainingPlain.dto.request;

import br.com.joaojuniodev.corefitpro.trainingItem.enums.DaysOfWeek;
import java.util.List;
import java.util.UUID;

public record TrainingPlanSaveDTO(UUID id, String description, String objective, String goal,
                                  UUID personalTrainerId, UUID traineeId, List<DayRequest> trainings) {
    public record DayRequest(DaysOfWeek dayOfWeek, UUID trainingId) {}
}
