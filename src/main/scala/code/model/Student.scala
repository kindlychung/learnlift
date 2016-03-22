package code.model

import net.liftweb.mapper._

/**
  * User: ggarcia
  * Date: 4/20/13
  * Time: 11:46 AM
  */
class Student extends LongKeyedMapper[Student]
  with ManyToMany
  with IdPK {
  def getSingleton = Student

  object name extends MappedString(this, 50)

  object courses extends MappedManyToMany(CourseStudents, CourseStudents.student, CourseStudents.course, Course)
}


object Student extends Student with LongKeyedMetaMapper[Student] {
  override def dbTableName = "students"
}
