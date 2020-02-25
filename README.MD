#Custom (Extended) HealthCheck AEM

clean install -PautoInstallPackage

http://localhost:4502/system/health

http://localhost:4502/system/health?tags=shallow

http://localhost:4502/system/health?tags=shallow,security

http://localhost:4502/system/health?tags=shallow,security&combineTagsOr=false