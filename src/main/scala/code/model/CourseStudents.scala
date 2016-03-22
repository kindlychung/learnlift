package code.model

import net.liftweb.mapper._

/**
  * User: ggarcia
  * Date: 4/20/13
  * Time: 11:53 AM
  */
class CourseStudents extends LongKeyedMapper[CourseStudents]
  with IdPK {
  def getSingleton = CourseStudents

  object course extends MappedLongForeignKey(this, Course)

  object student extends MappedLongForeignKey(this, Student)

}

object CourseStudents extends CourseStudents with LongKeyedMetaMapper[CourseStudents] {
  override def dbTableName = "courses_students"
}