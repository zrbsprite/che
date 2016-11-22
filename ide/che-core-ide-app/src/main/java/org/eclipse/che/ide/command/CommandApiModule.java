/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.command;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.inject.client.multibindings.GinMapBinder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import org.eclipse.che.ide.Resources;
import org.eclipse.che.ide.api.command.CommandManager3;
import org.eclipse.che.ide.api.command.CommandTypeRegistry;
import org.eclipse.che.ide.api.component.Component;
import org.eclipse.che.ide.api.component.WsAgentComponent;
import org.eclipse.che.ide.api.filetypes.FileType;
import org.eclipse.che.ide.command.manager.CommandManagerImpl3;
import org.eclipse.che.ide.command.node.NodeFactory;
import org.eclipse.che.ide.command.producer.CommandProducerActionFactory;
import org.eclipse.che.ide.command.producer.CommandProducerActionManager;

import static org.eclipse.che.ide.command.node.CommandFileNode.FILE_TYPE_EXT;

/**
 * GIN module for configuring Command API components.
 *
 * @author Artem Zatsarynnyi
 */
public class CommandApiModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(CommandTypeRegistry.class).to(CommandTypeRegistryImpl.class).in(Singleton.class);
        bind(CommandManager3.class).to(CommandManagerImpl3.class).in(Singleton.class);

        GinMapBinder.newMapBinder(binder(), String.class, WsAgentComponent.class)
                    .addBinding("Command Manager")
                    .to(CommandManagerImpl3.class);

        GinMapBinder.newMapBinder(binder(), String.class, Component.class)
                    .addBinding("CommandProducerActionManager")
                    .to(CommandProducerActionManager.class);

        install(new GinFactoryModuleBuilder().build(NodeFactory.class));

        install(new GinFactoryModuleBuilder().build(CommandProducerActionFactory.class));
    }

    @Provides
    @Singleton
    @Named("CommandFileType")
    protected FileType provideCommandFileType(Resources resources) {
        return new FileType(resources.defaultImage(), FILE_TYPE_EXT);
    }
}