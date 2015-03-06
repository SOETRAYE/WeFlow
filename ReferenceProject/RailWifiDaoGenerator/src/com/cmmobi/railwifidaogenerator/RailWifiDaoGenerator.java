/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cmmobi.railwifidaogenerator;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class RailWifiDaoGenerator {

    public static void main(String[] args) throws Exception {
    	Schema schema = new Schema(10, "com.cmmobi.railwifi.dao");


        addPassenger(schema);
        addOrderForm(schema);
        addTravelOrderInfoForm(schema);
        addFav(schema);
        addPlayHistory(schema);
        
        addDownloadHistory(schema);

        new DaoGenerator().generateAll(schema, "../RailWifi/src-gen");
    }

    private static void addPassenger(Schema schema) {
        Entity note = schema.addEntity("Passenger");
        note.addIdProperty().primaryKey().autoincrement();
        note.addStringProperty("user_id");
        note.addStringProperty("uuid").notNull();
        note.addStringProperty("nick_name");
        note.addStringProperty("telephone");
        note.addStringProperty("birther");
        note.addStringProperty("sex");
        note.addStringProperty("head_path");
        
    }
    
    private static void addOrderForm(Schema schema) {
    	Entity note = schema.addEntity("HistoryOrderForm");
        note.addIdProperty().primaryKey().autoincrement();
        note.addStringProperty("user_id");
        note.addStringProperty("uuid").notNull();
        note.addStringProperty("rail_num");
        note.addStringProperty("site_num");
        note.addStringProperty("nick_name");
        note.addStringProperty("telephone");
        note.addStringProperty("order_time");
        note.addStringProperty("total_price");
        note.addStringProperty("train_num");
        note.addStringProperty("order_code");
        note.addStringProperty("status");
        note.addStringProperty("content");
        note.addStringProperty("eat_position");// 0 座位 1餐车
        note.addStringProperty("site_count");
    }
    
        private static void addTravelOrderInfoForm(Schema schema) {
    	Entity note = schema.addEntity("TravelOrderInfo");
        note.addIdProperty().primaryKey().autoincrement();
        note.addStringProperty("user_id");
        note.addStringProperty("uuid").notNull();
        note.addStringProperty("tag");
        note.addStringProperty("color");
        note.addStringProperty("name");
        note.addStringProperty("fullname");
        note.addStringProperty("introduction");
        note.addStringProperty("line_id");
        note.addStringProperty("total_price");
        note.addStringProperty("adult_ticket");
        note.addStringProperty("child_ticket");
        note.addStringProperty("time");
        note.addStringProperty("username");
        note.addStringProperty("cellphone");
        note.addStringProperty("emailaddr");
        note.addStringProperty("idcardfnum");
        note.addStringProperty("order_time");
        note.addStringProperty("order_num");
        note.addStringProperty("ispaid");
        note.addStringProperty("img");
    }
    
    private static void addFav(Schema schema) {
        Entity note = schema.addEntity("Fav");
/*        note.addIdProperty().primaryKey().autoincrement();*/
        note.addStringProperty("media_id").primaryKey();
        note.addIntProperty("media_type").notNull(); //1 video 2 audio 3 joke 4 ebook
        note.addStringProperty("name");
        note.addStringProperty("img_path");
        note.addStringProperty("tag");
        note.addStringProperty("color");
        note.addStringProperty("introduction");
        note.addStringProperty("source");
        note.addStringProperty("source_id");
        note.addStringProperty("src_path");
        note.addStringProperty("director");
        note.addStringProperty("actors");
        note.addStringProperty("score");
    }
    
    private static void addPlayHistory(Schema schema) {
        Entity note = schema.addEntity("PlayHistory");
/*        note.addIdProperty().primaryKey().autoincrement();*/
        note.addStringProperty("media_id").primaryKey();
        note.addIntProperty("media_type").notNull(); //1 video 2 audio 3 joke 4 ebook
        note.addStringProperty("name");
        note.addStringProperty("img_path");
        note.addStringProperty("src_url");
        note.addStringProperty("location"); //00:28:07 绗洓绔犵涓冭妭  253椤�
        note.addStringProperty("percent"); //360000 毫秒
        note.addStringProperty("totaltime"); //总时长

    }
    
    
    private static void addDownloadHistory(Schema schema) {
        Entity note = schema.addEntity("DownloadHistory");
        note.addIntProperty("seqNo").primaryKey();
        note.addIntProperty("downloadType").notNull(); //MOVIE=1,MUSIC=2,BOOK=3,APP=4
        note.addIntProperty("downloadStatus").notNull(); //WAIT=1,PERPARE=2,RUN=3,PAUSE=4,DONE=5,FAIL=6
        note.addIntProperty("downloadSize").notNull();
        note.addIntProperty("wholeSize").notNull();
        note.addLongProperty("startTs").notNull();
        note.addLongProperty("endTs").notNull();
        note.addStringProperty("url").notNull(); //
        note.addStringProperty("path").notNull(); //
        note.addStringProperty("title"); //
        note.addStringProperty("detail"); //
        note.addStringProperty("picUrl"); //


    }

}
