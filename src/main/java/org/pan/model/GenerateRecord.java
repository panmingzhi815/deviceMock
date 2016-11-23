package org.pan.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by panmingzhi on 2016/11/20 0020.
 */
public class GenerateRecord {

    private SimpleBooleanProperty auto = new SimpleBooleanProperty(true);
    private SimpleStringProperty card = new SimpleStringProperty();
    private SimpleIntegerProperty number = new SimpleIntegerProperty(100);
    private SimpleStringProperty eventType = new SimpleStringProperty(EventType.随机.name());

    public boolean isAuto() {
        return auto.get();
    }

    public SimpleBooleanProperty autoProperty() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto.set(auto);
    }

    public String getCard() {
        return card.get();
    }

    public SimpleStringProperty cardProperty() {
        return card;
    }

    public void setCard(String card) {
        this.card.set(card);
    }

    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public String getEventType() {
        return eventType.get();
    }

    public SimpleStringProperty eventTypeProperty() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType.set(eventType);
    }

    @Override
    public String toString() {
        return "GenerateRecord{" +
                "auto=" + auto +
                ", card=" + card +
                ", number=" + number +
                ", eventType=" + eventType +
                '}';
    }
}
