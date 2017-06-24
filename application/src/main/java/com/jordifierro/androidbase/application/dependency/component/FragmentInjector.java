package com.jordifierro.androidbase.application.dependency.component;

import com.jordifierro.androidbase.application.view.fragment.LoginFragment;
import com.jordifierro.androidbase.application.view.fragment.NoteCreateFragment;
import com.jordifierro.androidbase.application.view.fragment.NoteDetailFragment;
import com.jordifierro.androidbase.application.view.fragment.NoteDetailFragmentForPager;
import com.jordifierro.androidbase.application.view.fragment.NoteEditFragment;
import com.jordifierro.androidbase.application.view.fragment.NotesFragment;
import com.jordifierro.androidbase.application.view.fragment.NotesViewPagerFragment;
import com.jordifierro.androidbase.application.view.fragment.RegisterFragment;
import com.jordifierro.androidbase.application.view.fragment.ResetPasswordFragment;
import com.jordifierro.androidbase.application.view.fragment.SettingsFragment;

public interface FragmentInjector {

    void inject(LoginFragment loginFragment);

    void inject(RegisterFragment registerFragment);

    void inject(NotesFragment notesFragment);

    void inject(NoteDetailFragment noteDetailFragment);

    void inject(NoteCreateFragment noteCreateFragment);

    void inject(NoteEditFragment noteEditFragment);

    void inject(SettingsFragment settingsFragment);

    void inject(ResetPasswordFragment resetPasswordFragment);

    void inject(NotesViewPagerFragment NotesViewPagerFragment);

    void inject(NoteDetailFragmentForPager noteDetailFragmentForPager);

}
