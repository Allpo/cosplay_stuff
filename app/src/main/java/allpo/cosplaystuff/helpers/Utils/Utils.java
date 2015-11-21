package allpo.cosplaystuff.helpers.Utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import allpo.cosplaystuff.datas.CheckElement;

/**
 * Created by Allpo on 05/09/2014.
 */
public class Utils {

    public static void sortCheckList(List<CheckElement> checkList){
        Collections.sort(checkList, new Comparator<CheckElement>() {
            @Override
            public int compare(CheckElement elt1, CheckElement elt2) {
                if (elt1.getPosition() < elt2.getPosition()){
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

    public static void swapEltInCheckList(List<CheckElement> list, int from, int to){
        if (list != null) {
            CheckElement item = list.get(from);
            list.remove(from);
            list.add(to, item);

            for (int i = 0; i < list.size(); i++){
                list.get(i).setPosition(i);
            }
        }
    }
}
