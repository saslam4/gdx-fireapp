/*
 * Copyright 2018 mk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.mk5.gdx.fireapp.e2e.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.database.ConnectionStatus;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.ListenerPromise;

public class DatabaseMaintenanceTest extends E2ETest {

    private ListenerPromise listenerPromise;

    @Override
    public void action() {
        final String stringValue = "abcd";

        GdxFIRDatabase.instance().inReference("/test").keepSynced(true);

        listenerPromise = GdxFIRDatabase.instance()
                .onConnect()
                .thenListener(new Consumer<ConnectionStatus>() {
                    @Override
                    public void accept(ConnectionStatus connectionStatus) {
                        Gdx.app.log("App", "connection: " + connectionStatus);
                        GdxFIRDatabase.instance().inReference("/test").keepSynced(false);
                        listenerPromise.cancel();
                        success();
                    }
                });
    }

    @Override
    public void draw(Batch batch) {
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
    }
}
