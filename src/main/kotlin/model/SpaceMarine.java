package model;

import xml.LocalDateTimeAdapter;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SpaceMarine")
@Entity
public class SpaceMarine {
  @XmlElement
  @Id
  @GeneratedValue
  private Long id = null;
  //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

  @XmlElement
  @NotNull(message = "Spacemarine should have a name")
  @NotBlank(message = "Name cannot be empty")
  private String name;

  @XmlElement
  @NotNull
  @Valid
  private Coordinates coordinates; //Поле не может быть null

  @XmlElement
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  @NotNull
  private LocalDateTime creationDate = LocalDateTime.now();
  //Поле не может быть null, Значение этого поля должно генерироваться автоматически

  @XmlElement
  @Min(value = 1, message = "Spacemarine should have their health greater than 0")
  private Long health; //Поле может быть null, Значение поля должно быть больше 0

  @XmlElement
  @Min(value = 1, message = "Spacemarine should have their health count greater than 0")
  @Max(value = 3, message = "Spacemarine cannot have more than 3 hearts")
  private Integer heartCount;      //Поле может быть null

  @XmlElement
  @Enumerated(EnumType.STRING)
  private AstartesCategory category; //Поле может быть null

  @XmlElement
  @Enumerated(EnumType.STRING)
  private MeleeWeapon meleeWeapon; //Поле может быть null

  @XmlElement
  @Valid
  private Chapter chapter; //Поле может быть null

  public SpaceMarine() { }

  public SpaceMarine(
      final String name,
      final Coordinates coordinates,
      final Long health,
      final Integer heartCount,
      final AstartesCategory category,
      final MeleeWeapon meleeWeapon,
      final Chapter chapter
  ) {
    this.name = name;
    this.coordinates = coordinates;
    this.health = health;
    this.heartCount = heartCount;
    this.category = category;
    this.meleeWeapon = meleeWeapon;
    this.chapter = chapter;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public Long getHealth() {
    return health;
  }

  public Integer getHeartCount() {
    return heartCount;
  }

  public AstartesCategory getCategory() {
    return category;
  }

  public MeleeWeapon getMeleeWeapon() {
    return meleeWeapon;
  }

  public Chapter getChapter() {
    return chapter;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setCoordinates(final Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public void setCreationDate(final LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public void setHealth(final Long health) {
    this.health = health;
  }

  public void setHeartCount(final Integer heartCount) {
    this.heartCount = heartCount;
  }

  public void setCategory(final AstartesCategory category) {
    this.category = category;
  }

  public void setMeleeWeapon(final MeleeWeapon meleeWeapon) {
    this.meleeWeapon = meleeWeapon;
  }

  public void setChapter(final Chapter chapter) {
    this.chapter = chapter;
  }
}