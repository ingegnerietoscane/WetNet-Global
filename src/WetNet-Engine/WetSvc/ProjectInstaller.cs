﻿/****************************************************************************
 * 
 * WetSvc - WetNet Engine Service.
 * Copyright 2013-2015 Ingegnerie Toscane S.r.l.
 * 
 * This file is part of WetNet application.
 * 
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * 
 * You may not use this work except in compliance with the licence.
 * You may obtain a copy of the Licence at:
 * http://ec.europa.eu/idabc/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 * 
 ***************************************************************************/

using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration.Install;
using System.Linq;
using System.Threading.Tasks;
using System.ServiceProcess;

namespace WetSvc
{
    /// <summary>
    /// Installatore del servizio
    /// </summary>
    [RunInstaller(true)]
    public partial class ProjectInstaller : System.Configuration.Install.Installer
    {
        #region Costruttore

        public ProjectInstaller()
        {
            // Inizializzazione automatica dei componenti
            InitializeComponent();
        }

        #endregion

        /// <summary>
        /// Evento di post-installazione, esegue in automatico il servizio
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ProjectInstaller_AfterInstall(object sender, InstallEventArgs e)
        {
            ServiceController sc = new ServiceController(serviceInstaller.ServiceName);
            if (sc.Status == ServiceControllerStatus.Stopped)
                sc.Start();
        }

        /// <summary>
        /// Evento di pre-disintallazione, tenta l'arresto del servizio
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ProjectInstaller_BeforeUninstall(object sender, InstallEventArgs e)
        {
            ServiceController sc = new ServiceController(serviceInstaller.ServiceName);
            if (sc.Status != ServiceControllerStatus.Running)
            {
                sc.Stop();
                // Attende un timeout di 1 minuto
                sc.WaitForStatus(ServiceControllerStatus.Stopped, new TimeSpan(0, 1, 0));
                // Controllo lo stato del servizio
                if (sc.Status != ServiceControllerStatus.Stopped)
                    throw new Exception("Unattemped error! Timeout occurred during service stop, try manually.");
            }
        }
    }
}
