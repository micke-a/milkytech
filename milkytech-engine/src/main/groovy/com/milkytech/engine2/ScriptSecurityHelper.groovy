package com.milkytech.engine2

import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.stmt.IfStatement
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer

/**
 *http://mrhaki.blogspot.co.uk/2014/04/groovy-goodness-restricting-script.html 
 */
class ScriptSecurityHelper {

    CompilerConfiguration createCompilerConfiguraton(){
        final SecureASTCustomizer astCustomizer = new SecureASTCustomizer(
            // Do not allow method creation.
            methodDefinitionAllowed: false,
        
            // Do not allow closure creation.
            closuresAllowed: false,
        
            // No package allowed.
            packageAllowed: false,
        
            // White or blacklists for imports.
            importsBlacklist: ['java.util.Date'],
            // or importsWhitelist
            staticImportsWhitelist: [],
            // or staticImportBlacklist
            staticStarImportsWhitelist: [],
            // or staticStarImportsBlacklist
        
            // Make sure indirect imports are restricted.
            indirectImportCheckEnabled: true,
        
            // Only allow plus and minus tokens.
            tokensWhitelist: [PLUS, MINUS, EQUAL],
            // or tokensBlacklist
        
            // Disallow constant types.
            constantTypesClassesWhiteList: [Integer, Object, String],
            // or constantTypesWhiteList
            // or constantTypesBlackList
            // or constantTypesClassesBlackList
            
            // Restrict method calls to whitelisted classes.
            // receiversClassesWhiteList: [],
            // or receiversWhiteList
            // or receiversClassesBlackList
            // or receiversBlackList
        
            // Ignore certain language statement by
            // whitelisting or blacklisting them.
            statementsBlacklist: [IfStatement],
            // or statementsWhitelist
        
            // Ignore certain language expressions by
            // whitelisting or blacklisting them.
            expressionsBlacklist: [MethodCallExpression]
            // or expresionsWhitelist
        )
        
        // Add SecureASTCustomizer to configuration for shell.
        final CompilerConfiguration conf = new CompilerConfiguration()
        conf.addCompilationCustomizers(astCustomizer)
        
        return conf
    }
}
