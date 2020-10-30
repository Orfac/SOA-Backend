package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Coordinates {
  @XmlElement
  @Min(value = -231, message = "X should be greater than -232")
  @Id
  private long x; //Значение поля должно быть больше -232
  @XmlElement
  @Min(value = 48, message = "Y should be greater than -47")
  @NotNull(message = "Coordinates should have y variable")
  @Id
  private Float y; //Значение поля должно быть больше -47, Поле не может быть null

  public Coordinates(){}
  public Coordinates(final long x, final Float y) {
    this.x = x;
    this.y = y;
  }

  public long getX() {
    return x;
  }

  public Float getY() {
    return y;
  }

  public void setX(final long x) {
    this.x = x;
  }

  public void setY(final Float y) {
    this.y = y;
  }
}
