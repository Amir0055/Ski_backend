package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
@Builder
public class Course implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long numCourse;
	private int level;
	@Enumerated(EnumType.STRING)
	private TypeCourse typeCourse;
	@Enumerated(EnumType.STRING)
	private Support support;
	private Float price;
	private int timeSlot;

	@JsonIgnore
	@OneToMany(mappedBy= "course")
	private Set<Registration> registrations;

}
