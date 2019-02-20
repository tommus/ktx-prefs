@file:Suppress("NOTHING_TO_INLINE")

package ${package};

import android.content.Context
import androidx.fragment.app.Fragment

inline fun Context.require${extensionClassName}(): ${prefWrapperClassName} =
    ${prefWrapperClassName}.get(this)

inline fun Fragment.require${extensionClassName}(): ${prefWrapperClassName} =
    requireContext().require${extensionClassName}()
