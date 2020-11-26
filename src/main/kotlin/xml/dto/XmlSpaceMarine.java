package xml.dto;

import model.AstartesCategory;
import model.Chapter;
import model.Coordinates;
import model.MeleeWeapon;
import model.SpaceMarine;
import org.eclipse.persistence.exceptions.JAXBException;
import static org.eclipse.persistence.exceptions.JAXBException.unknownTypeForVariableNode;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement(name = "SpaceMarine")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlSpaceMarine extends SpaceMarine {


  @XmlAnyElement(lax = false)
  private List<Object> any;

  public XmlSpaceMarine() {super(); any = null;}

  public SpaceMarine toSpaceMarine() {
    if (any == null || any.size() == 0){
      return this;
    }

    throw unknownTypeForVariableNode(any.get(0).toString());
  }

  public XmlSpaceMarine(final String name,
      final Coordinates coordinates,
      final LocalDateTime creationDate,
      final Long health,
      final Integer heartCount,
      final AstartesCategory category,
      final MeleeWeapon meleeWeapon,
      final Chapter chapter){
    super(name,coordinates, creationDate, health, heartCount, category, meleeWeapon, chapter);
    any = null;
  }

  public XmlSpaceMarine(final String name,
      final Coordinates coordinates,
      final LocalDateTime creationDate,
      final Long health,
      final Integer heartCount,
      final AstartesCategory category,
      final MeleeWeapon meleeWeapon,
      final Chapter chapter, final List<Object> any){
    super(name,coordinates, creationDate, health, heartCount, category, meleeWeapon, chapter);
    this.any = any;
  }
}
