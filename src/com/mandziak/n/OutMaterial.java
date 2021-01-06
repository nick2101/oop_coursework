package com.mandziak.n;

import com.mandziak.n.DBHandler;

public class OutMaterial {

    DBHandler dbHandler;

    OutMaterial()
    {
        dbHandler = new DBHandler();
    }

    public void readMaterialFromFile (String title)
    {
        //Читання навчального матеріалу з файла.
        dbHandler.getMaterial(title);
    }

    private void giveMaterial ()
    {
        //Надання студенту порції навчального матеріалу.
    }

    private void giveNextPart ()
    {
        //Перехід до наступної порції матеріалу.
    }
}

/*
Матеріал подається за схемою, що задається викладачем для кожного
конкретного випадку.



1.1.Жорстка, заздалегідь встановлена схема подання матеріалу.
1.2.Матеріал подається за вибором студента.
1.3.Матеріал подається за вибором, але враховуючи зв’язок тем.
*/