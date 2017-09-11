package com.example.admin.karsol_ano_1;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Admin on 27-04-2017.
 */

 public class TitleMenu extends ExpandableGroup<SubTitle>
{

        private String imageUrl;

        public TitleMenu(String title, List<SubTitle> items, String imageUrl) {
            super(title, items);
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

}
