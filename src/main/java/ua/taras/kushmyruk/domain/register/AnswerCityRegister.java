package ua.taras.kushmyruk.domain.register;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AnswerCityRegister {
  private List<AnswerCityRegistryItem> items;

   public List<AnswerCityRegistryItem> getItems() {
      return items;
   }

   public void addItem(AnswerCityRegistryItem item){
     if (items == null){
        items = new ArrayList<>(10);
     }
     items.add(item);

  }
}
