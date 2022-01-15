import dataEditingForSaving as dEFS
#import tensorflow as tf
import numpy as np




def toCNN(path):
    print("Ollaan Machine Learning osuudessa  ja funktiossa toCNN ja saatiin parametrinä: " + path)
    readyForSaving(path)
    #CNN(path)



def CNN(path):
    print("Ollaan Machine Learningissa CNN funktiossa")
    #Tässä itse opetettu malli ja sen läpi laittaminen

    #readyForSaving(path)



def readyForSaving(path):
    print("Ollaan Machine Learningissa readyForSaving funktissa")
    #Tässä lähetys tallenukseen dataEditingForSaving scriptille
    #Ensimmäisenä parametrinä siis se ennustus tulos mikä onkaan, tässä olen kokeilun vuoksi laitanut 3, mikä oli siis D
    dEFS.dataReceive(str(3),path)