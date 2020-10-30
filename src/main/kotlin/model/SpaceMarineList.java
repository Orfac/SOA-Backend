package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "SpaceMarineCollection")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceMarineList{

  @XmlElement(name = "SpaceMarine")
  private List<SpaceMarine> marines;

  public SpaceMarineList(final List<SpaceMarine> marines) {this.marines = marines;}
  public SpaceMarineList(){}

  public List<SpaceMarine> getMarines() {
    return marines;
  }

  public void setMarines(final List<SpaceMarine> marines) {
    this.marines = marines;
  }
}
