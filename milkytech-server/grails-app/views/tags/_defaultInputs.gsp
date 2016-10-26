<g:each in="${inputs}" var="inp">
    <div class="form-group">
        <label for="${inp.name }" class="col-sm-2 control-label">${inp.name }</label>
        <div class="col-sm-10">
            <g:if test="${inp.mandatory }">
                <g:if test="${inp.widget == 'textarea' }">
                    <g:textArea class="form-control" name="${inp.name }"></g:textArea>
                </g:if>
                <g:else>
                    <g:textField class="form-control" name="${inp.name }"/>
                </g:else>
            </g:if>
            <g:else>
                Not mandatory
            </g:else>
        </div>
    </div>
</g:each>

