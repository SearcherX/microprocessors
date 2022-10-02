package homework.microprocessors.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Microprocessor implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String model;
    private int dataBitDepth;
    private int addressBitDepth;
    private final ArrayList<ClockSpeed> clockSpeeds = new ArrayList<>();
    private long addressSpaces;
    private Integer numberOfCommands;
    private int numberOfElements;
    private int releaseYear;

    public Microprocessor() {
    }

    public Microprocessor(String model, int dataBitDepth, int addressBitDepth, long addressSpaces, Integer numberOfCommands, int numberOfElements, int releaseYear) {
        this.model = model;
        this.dataBitDepth = dataBitDepth;
        this.addressBitDepth = addressBitDepth;
        this.addressSpaces = addressSpaces;
        this.numberOfCommands = numberOfCommands;
        this.numberOfElements = numberOfElements;
        this.releaseYear = releaseYear;
    }

    public Microprocessor(int id, String model, int dataBitDepth, int addressBitDepth, long addressSpaces, Integer numberOfCommands, int numberOfElements, int releaseYear) {
        this.id = id;
        this.model = model;
        this.dataBitDepth = dataBitDepth;
        this.addressBitDepth = addressBitDepth;
        this.addressSpaces = addressSpaces;
        this.numberOfCommands = numberOfCommands;
        this.numberOfElements = numberOfElements;
        this.releaseYear = releaseYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getDataBitDepth() {
        return dataBitDepth;
    }

    public void setDataBitDepth(int dataBitDepth) {
        this.dataBitDepth = dataBitDepth;
    }

    public int getAddressBitDepth() {
        return addressBitDepth;
    }

    public void setAddressBitDepth(int addressBitDepth) {
        this.addressBitDepth = addressBitDepth;
    }

    public ArrayList<ClockSpeed> getClockSpeeds() {
        return clockSpeeds;
    }

    //преобразовать список частот в строку для вывода в текстовое поле
    public String getClockSpeedsStr() {
        StringBuilder sb = new StringBuilder();
        for (ClockSpeed clockSpeed: clockSpeeds) {
            sb.append(clockSpeed.toString());
            sb.append(" и ");
        }
        if (!sb.toString().equals(""))
            sb.delete(sb.length() - 3, sb.length());
        return sb.toString();
    }

    //парсить строку из текстового поля в список объектов частот
    public void setClockSpeeds(String clockSpeeds) {
        String[] csArr= clockSpeeds.replaceAll(" ", "").split("и");
        for (String cs: csArr) {
            String[] csValArr = cs.split("-");
            double min = Double.parseDouble(csValArr[0]);
            Double max = null;
            if (csValArr.length > 1)
                max = Double.parseDouble(csValArr[1]);
            this.clockSpeeds.add(new ClockSpeed(min, max));
        }
    }

    public long getAddressSpaces() {
        return addressSpaces;
    }

    public void setAddressSpaces(long addressSpaces) {
        this.addressSpaces = addressSpaces;
    }

    public Integer getNumberOfCommands() {
        return numberOfCommands;
    }

    public void setNumberOfCommands(Integer numberOfCommands) {
        this.numberOfCommands = numberOfCommands;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void addClockSpeed(ClockSpeed clockSpeed) {
        clockSpeeds.add(clockSpeed);
    }
}
