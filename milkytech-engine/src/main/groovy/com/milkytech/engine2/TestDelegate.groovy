package com.milkytech.engine2

class TestDelegate {

    CalculationContext calcContext
    List<String> errors = []

    def methodMissing(String name, args) {
        println "Comparing $name with ${args[0]}"
        return compareValues(calcContext[name], args[0])
    }

    private boolean compareMaps(Map m1, Map m2) {
        boolean result = true
        m2.each { m2kvp ->
            if (!m1.containsKey(m2kvp.key)) {
                errors.add "Value $m2kvp.key not found."
                result = false
            } else {
                result = result && compareValues(m2kvp.value, m1[m2kvp.key])
            }
        }
        return result
    }

    private boolean compareLists(List l1, List l2) {
        boolean result = true
        l1.eachWithIndex {el, i ->
            result = result && compareValues(el, l2[i])
        }
        return result
    }

    private boolean compareValues(v1, v2) {
        if (v1 instanceof Map) {
            if (!compareMaps(v1, v2)) {
                errors.add "Maps are not equal:"
                errors.add "Fact: $v1\nExpected: $v2"
                return false
            }
            return true
        } else if (v1 instanceof List) {
            if (!compareLists(v1, v2)) {
                errors.add "Lists are not equal:"
                errors.add "Fact: $v1\nExpected: $v2"
                return false
            }
            return true
        } else {
            if (v1 != v2) {
                errors.add "Values are not equal:"
                errors.add "(${v1?.class}) $v1 != (${v2?.class}) $v2"
                return false
            }
            return true
        }
    }

}
