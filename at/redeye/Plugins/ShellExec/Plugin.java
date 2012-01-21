/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.redeye.Plugins.ShellExec;

import at.redeye.FrameWork.base.AutoMBox;
import at.redeye.FrameWork.base.Root;
import at.redeye.FrameWork.base.Setup;
import at.redeye.FrameWork.widgets.helpwindow.HelpFileLoader;
import at.redeye.FrameWork.widgets.helpwindow.HyperlinkExecuter;
import at.redeye.FrameWork.widgets.helpwindow.OpenUrlInterface;

/**
 *
 * @author martin
 */
public class Plugin implements at.redeye.FrameWork.Plugin.Plugin, OpenUrlInterface
{    
    public static String NAME = "ShellExec";
    
    Root root;

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public String getLicenceText()
    {
        final StringBuilder builder = new StringBuilder();

        new AutoMBox( Plugin.class.getSimpleName() )
        {
            @Override
            public void do_stuff() throws Exception {
                HelpFileLoader helper = new HelpFileLoader();

                String licence = helper.loadHelp("/at/redeye/Plugins/ShellExec", "Licence");
                builder.append(licence);
            }
        };
        

        return builder.toString();
    }

    @Override
    public void initPlugin(Object obj) 
    {
        if( obj != null  && obj instanceof Root )
        {
            root = (Root) obj;
            root.addDllExtractorToCache(new HSWChellExecDLL());

            if( Setup.is_win_system() )
                HyperlinkExecuter.setOpenUrl(this);
            
            ShellExec.init(obj.getClass());
        }
    }

    @Override
    public boolean isAvailable() {
        if( Setup.is_win_system() )
            return true;

        return false;
    }

    @Override
    public String toString()
    {
        return getName() + " " + getVersion();
    }

    @Override
    public String getChangeLog() {

        final StringBuilder builder = new StringBuilder();

        new AutoMBox( Plugin.class.getSimpleName() )
        {
            @Override
            public void do_stuff() throws Exception {
                HelpFileLoader helper = new HelpFileLoader();

                String changelog = helper.loadHelp("/at/redeye/Plugins/ShellExec", "ChangeLog");
                builder.append(changelog);
            }
        };


        return builder.toString();
    }

    @Override
    public String getVersion() {
        return Version.getVersion();
    }

    @Override
    public void openUrl(String url)
    {
        ShellExec shell = new ShellExec();
        shell.execute(url);
    }
}
