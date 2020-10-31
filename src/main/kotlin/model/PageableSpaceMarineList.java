package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "SpaceMarineCollection")
@XmlAccessorType(XmlAccessType.FIELD)
public class PageableSpaceMarineList{

  @XmlElement(name = "SpaceMarine")
  private List<SpaceMarine> marines;

  @XmlElement(name = "PageSize")
  private int pageSize;
  @XmlElement(name = "PageNumber")
  private int pageNumber;

  public PageableSpaceMarineList(
      final List<SpaceMarine> marines,
      final int pageSize,
      final int pageNumber
  ) { this.marines = marines;
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
  }
  public PageableSpaceMarineList(){}

  public List<SpaceMarine> getMarines() {
    return marines;
  }

  public int getPageSize() {
    return pageSize;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setMarines(final List<SpaceMarine> marines) {
    this.marines = marines;
  }

  public void setPageSize(final int pageSize) {
    this.pageSize = pageSize;
  }

  public void setPageNumber(final int pageNumber) {
    this.pageNumber = pageNumber;
  }
}
