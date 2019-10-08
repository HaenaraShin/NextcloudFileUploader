Nextcloud File Uploader  
=============================

**Kotlin program to upload files to NextCloud on cli.**

## Purpose

Upload any files on your own NextCloud with this. Without api, NextCloud can be a deployment automation server.

## Run

```
$ java -jar NextCloud NextcloudFileUploader.jar -id {ID} -pwd {PASSWORD} -cloudUrl {NextCloud URL} -uploadPath {Upload path} -filePath {Local file path to upload} -driverPath {ChromeDriver path} -timeout {Timtout (sec)}

```


## Usage
If your team uses NextCloud, you may use as gradle task to distribute you application simply.
```
task devAppUpload(type: JavaExec, group: "__upload__") {
  main = "NextCloudUploaderKt"
  classpath = files("$rootDir/nx_uploader/NextCloudUploader.jar")
  args('-id', 'admin'
      '-pwd', 'admin123!'
      '-cloudUrl', 'https://nextcloud.ubpay.com/index.php/apps/files?dir='
      '-uploadPath', '/Android/ubpay/dev'
      '-filePath', "$projectDir/build/outputs/apk/" + getApkName(),
      '-driverPath', "$rootDir/nx_uploader"
      '-timeout', '15'
  )
  doLast {
    println '------------------------ UPLOAD IS DONE -----------------------'
  }
}
```

...and of course, CI/CD is obviously much better way to distribute your app.