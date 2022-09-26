# LogNote

Android logcat viewer for Windows, Linux, Mac

Kotlin + swing

Regular expression filter

Online / offline log view

Aging Test : Save split file by lines

![lognote_light_scheme](https://user-images.githubusercontent.com/75207513/191988798-b0bf7fbd-1625-4606-9c53-bfd0ddeac6d3.png)
<br/>

# Run
Windows : start javaw -Dfile.encoding=utf8 -Xmx1024m -jar LogNote.jar\
Linux : java -Dfile.encoding=utf8 -Xmx2048m -jar LogNote.jar\
Mac : java -Dfile.encoding=utf8 -Xmx2048m -jar LogNote.jar

<br/>

# Config path
Save to the path set in the environment variable "LOGNOTE_HOME"\
Default current path

<br/>

# Adb setting
Set the adb path(to view online log) \
![lognote_adb_setting](https://user-images.githubusercontent.com/75207513/166401425-faaa46a9-da64-44df-bfd4-21733b694929.png)

<br/>

# Filter combobox color tag
![Lognote_ColorTag](https://user-images.githubusercontent.com/75207513/191993351-396498bc-d5f7-4b92-9a4b-e1b85cb87305.gif)

<br/>

# Filter combobox style
Setting > Filter Style : set style, color
1. Single line
2. Single line - highlight\
![lognote_singleline](https://user-images.githubusercontent.com/75207513/162203519-27a485f4-df90-4f33-8ae5-698957abea49.PNG)
3. Multi line
4. Multi line - highlight\
![lognote_multiline](https://user-images.githubusercontent.com/75207513/162203533-3bf194e8-a093-45a1-a82c-ba1b50bbf118.PNG)

Highlight color : Include text, Exclude text, Separator

<br/>

# Filter combobox size
![lognote_filtercombo_disable](https://user-images.githubusercontent.com/75207513/167983195-848e7aba-123f-44c8-ba7a-944d9923a1a1.gif)

<br/>

# Filter manager
Click : replace\
Ctrl + Click : append\
![Lognote_FilterManager](https://user-images.githubusercontent.com/75207513/191995297-2417f744-4247-4a33-9914-90ca6a758fc3.gif)

<br/>

# Color settings
Light(default) \
![lognote_light_scheme](https://user-images.githubusercontent.com/75207513/191988798-b0bf7fbd-1625-4606-9c53-bfd0ddeac6d3.png)

Dark \
![lognote_dark_scheme](https://user-images.githubusercontent.com/75207513/191988787-03f75360-8caa-4e7f-8eb7-a769b17a9677.png)

\
Setting > Appearance \
![lognote_appearance_settings](https://user-images.githubusercontent.com/75207513/183441901-de5dbfb4-1b4d-4dca-97a6-cee050d4bd28.png)

<br/>

# Save split file by lines for aging test
![aging](https://user-images.githubusercontent.com/75207513/150263408-d64b7003-6b9c-460f-a4e6-02e6a4ee01e9.png) \
Each time 100000 lines are saved, it is changed to a new file
