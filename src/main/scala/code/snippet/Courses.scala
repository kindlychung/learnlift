package code.snippet

import net.liftweb.util.BindHelpers._
import code.model.{Course, Student}

/**
  * User: ggarcia
  * Date: 4/20/13
  * Time: 12:19 PM
  */
class Courses {
  def prepareCourses_!() {
    Student.findAll().map(_.delete_!)
    Course.findAll().map(c => {
      c.students.delete_!
      c.delete_!
    })

    val studentNames = "John" :: "Joe" :: "Lisa" :: Nil
    val courseNames = "Math" :: "Biology" :: "Physics" :: Nil

    val students: List[Student] = studentNames.map(Student.create.name(_).saveMe())

    courseNames.map(courseName => {
      val course = Course.create.name(courseName)
      course.students.appendAll(students)
      course.save
    })
  }

  def list = {
    prepareCourses_!()

    ".course *" #> Course.findAll().map {
      c => {
        ".name *" #> c.name.get &
          ".student *" #> c.students.map(_.name.get)
      }
    }
  }
}
