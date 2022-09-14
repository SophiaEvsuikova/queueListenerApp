package project

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

@Component
class DataBaseService (@Value("\${app.url}") val url: String,
                       @Value("\${app.username}") val username: String,
                       @Value("\${app.password}") val password: String) {

    val connection: Connection = DriverManager
        .getConnection(url, username, password)


    fun insert(name: String, lastname: String)
    {
        val insertStatement = connection.prepareStatement(
            "INSERT INTO public.person(name, lastname) VALUES (?, ?)")

        insertStatement.setString(1, name)
        insertStatement.setString(2, lastname)
        insertStatement.executeUpdate()
    }

    fun findPerson(name: String, lastname: String): Int {

        val selectStatement = connection.prepareStatement(
            "SELECT * FROM public.person WHERE (name = ? and lastname = ?)",
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE)
        selectStatement.setString(1, name)
        selectStatement.setString(2, lastname)
        val resultSet = selectStatement.executeQuery()
        var size = 0
        if (resultSet != null) {
            resultSet.last()
            size = resultSet.row
        }

        return size
    }
}