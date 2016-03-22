package code.model

import net.liftweb.mapper._

/**
  * User: ggarcia
  * Date: 4/20/13
  * Time: 11:46 AM
  */
class Course extends LongKeyedMapper[Course]
  with ManyToMany
  with IdPK {
  def getSingleton = Course

  object name extends MappedString(this, 50)

  object students extends MappedManyToMany(CourseStudents, CourseStudents.course, CourseStudents.student, Student)
}

object Course extends Course with LongKeyedMetaMapper[Course] {
  override def dbTableName = "courses"

}
