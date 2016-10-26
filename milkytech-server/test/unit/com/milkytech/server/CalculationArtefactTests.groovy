package com.milkytech.server



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(CalculationArtefact)
class CalculationArtefactTests {

    void testSomething() {
        Map data = [
            inp1 : 1L,
            inp2 : "2"
            ]
        
        def cas =  CalculationArtefact.build(data, 'I')
        assert cas.size() == 2
        assert cas[0].type == 'I'
        assert cas[0].valueType == 'java.lang.Long'
        assert cas[0].value == '1'
        
        assert cas[1].type == 'I'
        assert cas[1].valueType == 'java.lang.String'
        assert cas[1].value == '2'
    }
}
