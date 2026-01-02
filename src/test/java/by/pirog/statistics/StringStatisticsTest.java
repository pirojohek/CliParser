package by.pirog.statistics;

import by.pirog.statistics.stringStatistics.StringStatistics;
import by.pirog.statistics.stringStatistics.SyncStringStatistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringStatisticsTest {

    @Test
    void accept_string_correctInfo(){
        StringStatistics stringStatistics = new SyncStringStatistics();
        stringStatistics.accept("worlddd");
        stringStatistics.accept("hello");
        stringStatistics.accept("ol");

        assertEquals(3, stringStatistics.getCount());
        assertEquals(2, stringStatistics.getMinLength());
        assertEquals(7, stringStatistics.getMaxLength());
    }
}
