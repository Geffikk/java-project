package sample.source.imap;

import java.time.LocalTime;
import java.sql.Time;

public interface TimerUpdate {
    void update(Time mapTime);
}
