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
package org.eclipse.che.ide.api.command;

import org.eclipse.che.api.promises.client.Promise;

import java.util.List;

/**
 * Facade for command related operations.
 *
 * @author Artem Zatsarynnyi
 */
public interface CommandManager3 {

    /** Returns workspace commands. */
    List<ContextualCommand> getCommands();

    /**
     * Creates new command of the specified type with the given {@link ApplicableContext}.
     * <p><b>Note</b> that command's name will be generated by {@link CommandManager3}
     * and command line will be provided by an appropriate {@link CommandType}.
     */
    Promise<ContextualCommand> createCommand(String commandTypeId, ApplicableContext applicableContext);

    /**
     * Creates copy of the given {@code command}.
     * <p><b>Note</b> that name of the created command may differ from
     * the given {@code command}'s name in order to prevent name duplication.
     */
    Promise<ContextualCommand> createCommand(ContextualCommand command);

    /**
     * Updates the command with the specified {@code name} by replacing it with the given {@code command}.
     * <p><b>Note</b> that name of the updated command may differ from the name provided by the given {@code command}
     * in order to prevent name duplication.
     */
    Promise<ContextualCommand> updateCommand(String name, ContextualCommand command);

    /** Removes command with the specified {@code commandName}. */
    Promise<Void> removeCommand(String commandName);

    void addCommandChangedListener(CommandChangedListener listener);

    void removeCommandChangedListener(CommandChangedListener listener);

    /** Listener that will be called when command has been changed. */
    interface CommandChangedListener {

        void onCommandAdded(ContextualCommand command);

        void onCommandUpdated(ContextualCommand command);

        void onCommandRemoved(ContextualCommand command);
    }
}