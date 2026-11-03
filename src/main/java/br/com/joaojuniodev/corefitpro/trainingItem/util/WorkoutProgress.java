package br.com.joaojuniodev.corefitpro.trainingItem.util;

import br.com.joaojuniodev.corefitpro.trainingItem.model.TrainingItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;

public final class WorkoutProgress {
    public static final ZoneId ZONE = ZoneId.of("America/Sao_Paulo");

    private WorkoutProgress() {}

    public static LocalDate weekStart() {
        return LocalDate.now(ZONE).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public static boolean isCompletedThisWeek(TrainingItem item) {
        if (!Boolean.TRUE.equals(item.getCompleted()) || item.getDateTime() == null) return false;
        LocalDate completedDate = item.getDateTime().toLocalDate();
        LocalDate monday = weekStart();
        return !completedDate.isBefore(monday) && completedDate.isBefore(monday.plusWeeks(1));
    }

    public static double percentage(long completed, long total) {
        if (total <= 0) return 0.0;
        long validCompleted = Math.max(0, Math.min(completed, total));
        return BigDecimal.valueOf(validCompleted).multiply(BigDecimal.valueOf(100))
            .divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP).doubleValue();
    }
}
