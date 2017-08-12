package hands.spock

import com.hands.desafio.gateway.InMemoryUserGateway
import com.hands.desafio.usecase.UserService
import com.hands.desafio.usecase.convert.csv.UserConverter
import com.hands.desafio.usecase.impl.UserServiceImpl
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

import static org.assertj.core.api.Assertions.assertThat

@Unroll
@Narrative('User Search')
@Title('User Search')
class UserServiceSpecTest extends Specification {

    def userGateway = new InMemoryUserGateway()
    def userService = new UserServiceImpl(userGateway, new UserConverter())

    def "Consultar um User por id"() {
        userGateway.clearCache()
        populateCache(userService)

        given: "Dado uma consulta de User por id "

        def useId = "5049527c-070b-46d3-9109-85bf9b7e8b53"

        when: "quando for feita a solicitacao de consulta"
        def userResponse = userService.findById(useId)

        then: "será gerado a consulta de um UserResponse"
        assertThat(userResponse.getId()).isEqualTo(useId)
    }

    private void populateCache(UserService userService) {
        def line = "5049527c-070b-46d3-9109-85bf9b7e8b53;IPHONE"
        def line2 = "5049527c-070b-46d3-9109-85bf9b7e8b53;SAMSUNG"
        userService.importUsers(line, line2)
    }
}
