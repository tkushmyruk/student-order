package ua.taras.kushmyruk.domain;

public class CountyArea {
    private String areaId;
    private String areaName;

    public CountyArea(String areaId, String areaName) {
        this.areaId = areaId;
        this.areaName = areaName;
    }

    public CountyArea() {
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "CountyArea{" +
                "areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                '}';
    }
}
