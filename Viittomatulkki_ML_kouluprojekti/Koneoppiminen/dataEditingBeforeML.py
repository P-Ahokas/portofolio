import machineLearning as ML
import os
import cv2
import numpy as np




def img_Or_Vid(path):                       #katsotaan onko kuva vai video nimen perusteella eli onko .jpg vai .mp4
    print("Ollaan funktiossa img_Or_Vid")

    for file in os.listdir(path):
        if file.endswith(".jpg"):
            path = os.path.join(path, file)
            print("Kuva oli: " + path)
            return 1,path
        elif file.endswith(".mp4"):
            path = os.path.join(path, file)
            print("Video oli: " + path)
            return 2,path
        else:
            print("Ei tunnistanut kuvaksi (.jpg) eikä videoksi (.mp4)")

        break               #En ole varma onko tarpeellinen



def img_Handling(whereGet,whereSave):
    print("Nyt ollaan img_Handling funktiossa")

    pic = cv2.imread(whereGet)                                                          #Lukee kuvan tiedostosta.
    #print(pic)

    nameOfData = whereSave + "img.jpg"                                                  #Asetetaan tallennus paikka kuvalle, eli tempForData\\img.jpg

    aPic = cv2.resize(pic,(400,256),fx = 0,fy = 0, interpolation = cv2.INTER_CUBIC)     #Kuvan pienentäminen
    bPic = cv2.cvtColor(aPic, cv2.COLOR_BGR2GRAY)                                       #Kuvan tekeminen harmaaksi
    cv2.imwrite(nameOfData, bPic)

    editedData(nameOfData)



def vid_Handling(whereGet,whereSave):
    print("Nyt ollaan vid_Handling funktiossa")
    #print("whereGet on: " + whereGet)
    #print("whereSave on: " + whereSave)

    vid_capture = cv2.VideoCapture(whereGet)                                                #openCV käyttäen avataan video tiedosto

    nameOfData = whereSave + "vid.mp4"                                                      #Asetetaan tallennus paikka videolle, eli tempForData\\vid.mp4

    fourcc = cv2.VideoWriter_fourcc(*'mp4v')                                                #Asetetaan codec mp4 se on *'mp4v', voi myös kokeilla #'m','p','4','v'   *"MP4V"   codec vaihtelee mediatyypin mukaan

    out = cv2.VideoWriter(nameOfData,fourcc, 7.0, (400,256), isColor=False)                 #Videon kirjoittamista varten asetetaan sille parametrejä, nameOfData = mihin tallennetaan, fourcc = codec arvo, 7.0 = fps, (400x256) = pikseli koko, isColor=False = kirjoitetaan harmaana

    while(vid_capture.isOpened()):                                                  
        ret, frame = vid_capture.read()                                                     #Kun video saadaan auki niin aletaan videon lukeminen kuva kuvalta (koska videohan on periaatteessa monta kuvaa peräkkäin)

        if ret == True:                                                                     #videoCapture.read() palautta return valuen (tässä jok False tai True) ja kuvan, eli jos frame muuttujaan on saatu jotain niin ret on True eli lukeminen onnistui
            a = cv2.cvtColor(frame,cv2.COLOR_BGR2GRAY)                                      #Alkuperäinen video on värillisenä niin se pitää tässä muuttaa harmaaksi
            b = cv2.resize(a,(400,256),fx = 0,fy = 0, interpolation = cv2.INTER_CUBIC)      #Muokataan kuva pienemmäksi (400x256) alkuperäisestä, fx ja fx eli x ja y akselilla pienentäminen, esim jos laittaisi fx,fy= 0,5 niin pienentäisi kuvaa puolella
            out.write(b)

        else:
            break

    vid_capture.release()           #Päästetään irti kaikki ohjelmisto ja hardware resurssit, esim ennen tämän tekemistä ei voi uudestaan alottaa tallentamista
    out.release()
    cv2.destroyAllWindows()

    editedData(nameOfData)



def editedData(newPath):
    print("Ollaan editedData funktiossa ja saatiin kohteeksi: " + newPath + " ,mikä lähetetään opetetun mallin läpi")

    ML.toCNN(newPath)                    #Lähetettään MachineLearningiin muokatun kuvan polku




#Paikka mistä kuva on
path = "C:\\Users\\reett\\Pictures\\Camera Roll\\Testi\\Kokeilu"
#Kutsutaan img_Or_Vid funktiota, siltä saadaan takaisin state eli 1 tai 2 ja kokonainen polku, eli esim tuohon jono jatkoksi tulee \\Kokeilu\\img.jpg
state,myPath = img_Or_Vid(path)

if state == 1:
    print("Tila oli 1, eli käsitellään kuva")
    whereGet = myPath
    whereSave = "tempForData\\"                 #Mihin data sitten käsittelyn jälkeen tallennetaan
    img_Handling(whereGet,whereSave)            #Annetaan käsittely funktiolle koko polku ja mihin tallennetaan
elif state == 2:
    print("Tila oli 2, eli käsitellään video")
    whereGet = myPath
    whereSave = "tempForData\\"
    vid_Handling(whereGet,whereSave)
else:
    print("Nyt meni joku vikaan, ei ohjelman mukaan saatu kuvaa tai videota. HUOM oikeat muodot .jpg ja .mp4")