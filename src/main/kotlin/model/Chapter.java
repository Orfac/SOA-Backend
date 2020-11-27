package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement(name = "Chapter")
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class Chapter implements Serializable {
  @XmlElement
  @NotNull(message = "Chapter should have a name")
  @NotBlank(message = "Chapter's name should not be blank")
  @Column(name = "chapter_name")
  private String name; //Поле не может быть null, Строка не может быть пустой

  @XmlElement
  private String parentLegion;

  @XmlElement
  @Max(value = 1000, message = "Chapter can contain less or equal 1000 marines")
  @Min(value = 1, message = "Chapter should contain at least 1 marine")
  private Long marinesCount;
      //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 1000

  @XmlElement
  private String world; //Поле может быть null

  public Chapter() {}

  public Chapter(
      final String name,
      final String parentLegion,
      final Long marinesCount,
      final String world
  ) {
    this.name = name;
    this.parentLegion = parentLegion;
    this.marinesCount = marinesCount;
    this.world = world;
  }

  public String getName() {
    return name;
  }

  public String getParentLegion() {
    return parentLegion;
  }

  public Long getMarinesCount() {
    return marinesCount;
  }

  public String getWorld() {
    return world;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setParentLegion(final String parentLegion) {
    this.parentLegion = parentLegion;
  }

  public void setMarinesCount(final Long marinesCount) {
    this.marinesCount = marinesCount;
  }

  public void setWorld(final String world) {
    this.world = world;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Chapter chapter = (Chapter) o;
    return Objects.equals(name, chapter.name) &&
        Objects.equals(parentLegion, chapter.parentLegion) &&
        Objects.equals(marinesCount, chapter.marinesCount) &&
        Objects.equals(world, chapter.world);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, parentLegion, marinesCount, world);
  }
}